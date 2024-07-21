package guru.sfg.beer.order.service.web.mappers;

import guru.sfg.beer.order.service.domain.BeerOrderLine;
import guru.sfg.beer.order.service.services.beer.BeerService;
import guru.sfg.beer.order.service.web.model.BeerDto;
import guru.sfg.beer.order.service.web.model.BeerOrderLineDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Optional;

public  abstract class BeerOrderLineMapperDecorator implements   BeerOrderLineMapper {

    private BeerOrderLineMapper beerOrderLineMapper;
    private BeerService beerService;
    @Autowired
    public void setBeerService(BeerService beerService) {
        this.beerService = beerService;
    }

    @Qualifier("delegate")
    @Autowired
    public void setBeerOrderLineMapper(BeerOrderLineMapper beerOrderLineMapper) {
        this.beerOrderLineMapper = beerOrderLineMapper;
    }
    @Override //TODO I dont undersatan very well here
    public BeerOrderLineDto beerOrderLineToDto(BeerOrderLine beerOrderLine) {

       BeerOrderLineDto beerOrderLineDto=beerOrderLineMapper.beerOrderLineToDto(beerOrderLine);
       Optional<BeerDto> beerDtoOptional = beerService.getBeerByUpc(beerOrderLine.getUpc());

        beerDtoOptional.ifPresent(beerDto -> {
           beerOrderLineDto.setBeerId(beerDto.getId());
           beerOrderLineDto.setBeerName(beerDto.getBeerName());
           beerOrderLineDto.setBeerStyle(beerDto.getBeerStyle());

        });
return beerOrderLineDto;

    }
}
